package foo.core.manager.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.common.email.EmailSender;
import foo.common.email.MessageTemplate;
import foo.common.page.Pagination;
import foo.common.security.BadCredentialsException;
import foo.common.security.UsernameNotFoundException;
import foo.common.security.encoder.PwdEncoder;
import foo.core.dao.UnifiedUserDao;
import foo.core.entity.UnifiedUser;
import foo.core.entity.Config.ConfigLogin;
import foo.core.manager.ConfigMng;
import foo.core.manager.UnifiedUserMng;

@Service
@Transactional
public class UnifiedUserMngImpl implements UnifiedUserMng {
	public UnifiedUser passwordForgotten(Integer userId, EmailSender email,
			MessageTemplate tpl) {
		UnifiedUser user = findById(userId);
		String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
		user.setResetKey(uuid);
		String resetPwd = RandomStringUtils.randomNumeric(10);
		user.setResetPwd(resetPwd);
		senderEmail(user.getId(), user.getUsername(), user.getEmail(), user
				.getResetKey(), user.getResetPwd(), email, tpl);
		return user;
	}

	private void senderEmail(final Integer uid, final String username,
			final String to, final String resetKey, final String resetPwd,
			final EmailSender email, final MessageTemplate tpl) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(email.getHost());
		sender.setUsername(email.getUsername());
		sender.setPassword(email.getPassword());
		sender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage,
						false, email.getEncoding());
				msg.setSubject(tpl.getForgotPasswordSubject());
				msg.setTo(to);
				msg.setFrom(email.getUsername(), email.getPersonal());
				String text = tpl.getForgotPasswordText();
				text = StringUtils.replace(text, "${uid}", String.valueOf(uid));
				text = StringUtils.replace(text, "${username}", username);
				text = StringUtils.replace(text, "${resetKey}", resetKey);
				text = StringUtils.replace(text, "${resetPwd}", resetPwd);
				msg.setText(text);
			}
		});
	}

	private void senderEmail(final String username, final String to,
			final String activationCode, final EmailSender email,
			final MessageTemplate tpl) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(email.getHost());
		sender.setUsername(email.getUsername());
		sender.setPassword(email.getPassword());
		sender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage,
						false, email.getEncoding());
				msg.setSubject(tpl.getRegisterSubject());
				msg.setTo(to);
				msg.setFrom(email.getUsername(), email.getPersonal());
				String text = tpl.getRegisterText();
				text = StringUtils.replace(text, "${username}", username);
				text = StringUtils.replace(text, "${activationCode}",
						activationCode);
				msg.setText(text);
			}
		});
	}

	public UnifiedUser resetPassword(Integer userId) {
		UnifiedUser user = findById(userId);
		user.setPassword(pwdEncoder.encodePassword(user.getResetPwd()));
		user.setResetKey(null);
		user.setResetPwd(null);
		return user;
	}

	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = configMng.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = user.getErrorCount();
		Date errorTime = user.getErrorTime();
		if (errorCount <= 0 || errorTime == null
				|| errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	public UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException {
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			updateLoginError(user.getId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		if (!user.getActivation()) {
			throw new BadCredentialsException("account not activated");
		}
		updateLoginSuccess(user.getId(), ip);
		return user;
	}

	public void updateLoginSuccess(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());

		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);

		user.setErrorCount(0);
		user.setErrorTime(null);
		user.setErrorIp(null);
	}

	public void updateLoginError(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = configMng.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Date errorTime = user.getErrorTime();

		user.setErrorIp(ip);
		if (errorTime == null
				|| errorTime.getTime() + errorInterval * 60 * 1000 < now
						.getTime()) {
			user.setErrorTime(now);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	public boolean usernameExist(String username) {
		return getByUsername(username) != null;
	}

	public boolean emailExist(String email) {
		return dao.countByEmail(email) > 0;
	}

	public UnifiedUser getByUsername(String username) {
		return dao.getByUsername(username);
	}

	public List<UnifiedUser> getByEmail(String email) {
		return dao.getByEmail(email);
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public UnifiedUser findById(Integer id) {
		UnifiedUser entity = dao.findById(id);
		return entity;
	}

	public UnifiedUser save(String username, String email, String password,
			String ip) {
		return save(username, email, password, ip, true, null, null);
	}

	public UnifiedUser save(String username, String email, String password,
			String ip, Boolean activation, EmailSender sender,
			MessageTemplate msgTpl) {
		Date now = new Timestamp(System.currentTimeMillis());
		UnifiedUser user = new UnifiedUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		user.setActivation(activation);
		user.init();
		dao.save(user);
		if (!activation) {
			String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
			user.setActivationCode(uuid);
			senderEmail(username, email, uuid, sender, msgTpl);
		}
		return user;
	}

	/**
	 * @see UnifiedUserMng#update(Integer, String, String)
	 */
	public UnifiedUser update(Integer id, String password, String email) {
		UnifiedUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
		return user;
	}

	public boolean isPasswordValid(Integer id, String password) {
		UnifiedUser user = findById(id);
		return pwdEncoder.isPasswordValid(user.getPassword(), password);
	}

	public UnifiedUser deleteById(Integer id) {
		UnifiedUser bean = dao.deleteById(id);
		return bean;
	}

	public UnifiedUser[] deleteByIds(Integer[] ids) {
		UnifiedUser[] beans = new UnifiedUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public UnifiedUser active(String username, String activationCode) {
		UnifiedUser bean = getByUsername(username);
		bean.setActivation(true);
		bean.setActivationCode(null);
		return bean;
	}

	public UnifiedUser activeLogin(UnifiedUser user, String ip) {
		updateLoginSuccess(user.getId(), ip);
		return user;
	}

	private ConfigMng configMng;
	private PwdEncoder pwdEncoder;
	private UnifiedUserDao dao;

	@Autowired
	public void setConfigMng(ConfigMng configMng) {
		this.configMng = configMng;
	}

	@Autowired
	public void setPwdEncoder(PwdEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
	}

	@Autowired
	public void setDao(UnifiedUserDao dao) {
		this.dao = dao;
	}

}