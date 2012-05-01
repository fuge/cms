package foo.common.fck;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * File Browser resource types. The File Browser provides a specific resource
 * type for each and every request. This class is intended to reflect these in
 * an Enum-like manner.
 * <p>
 * The resource types are:
 * <ul>
 * <li>File</li>
 * <li>Flash</li>
 * <li>Image</li>
 * <li>Media</li>
 * </ul>
 * 
 * @version $Id: ResourceType.java,v 1.1 2011/12/06 01:36:06 administrator Exp $
 */
public class ResourceType {

	/** The name of the resource type */
	private String name;
	/** The absolute path of the resource type */
	private String path;
	/** The allowed extensions */
	private Set<String> allowedEextensions;
	/** The denied extensions */
	private Set<String> deniedExtensions;

	/** Map holding a String to ResourceType reference */
	private static Map<String, ResourceType> types = new HashMap<String, ResourceType>(
			4);

	/** Resource type <code>File</code> */
	public static final ResourceType FILE = new ResourceType("File",
			PropertiesLoader.getFileResourceTypePath(), Utils
					.getSet(PropertiesLoader
							.getFileResourceTypeAllowedExtensions()), Utils
					.getSet(PropertiesLoader
							.getFileResourceTypeDeniedExtensions()));
	/** Resource type <code>Flash</code> */
	public static final ResourceType FLASH = new ResourceType("Flash",
			PropertiesLoader.getFlashResourceTypePath(), Utils
					.getSet(PropertiesLoader
							.getFlashResourceTypeAllowedExtensions()), Utils
					.getSet(PropertiesLoader
							.getFlashResourceTypeDeniedExtensions()));
	/** Resource type <code>Image</code> */
	public static final ResourceType IMAGE = new ResourceType("Image",
			PropertiesLoader.getImageResourceTypePath(), Utils
					.getSet(PropertiesLoader
							.getImageResourceTypeAllowedExtensions()), Utils
					.getSet(PropertiesLoader
							.getImageResourceTypeDeniedExtensions()));
	/** Resource type <code>Media</code> */
	public static final ResourceType MEDIA = new ResourceType("Media",
			PropertiesLoader.getMediaResourceTypePath(), Utils
					.getSet(PropertiesLoader
							.getMediaResourceTypeAllowedExtensions()), Utils
					.getSet(PropertiesLoader
							.getMediaResourceTypeDeniedExtensions()));

	static {
		types.put(FILE.getName(), FILE);
		types.put(FLASH.getName(), FLASH);
		types.put(IMAGE.getName(), IMAGE);
		types.put(MEDIA.getName(), MEDIA);
	}

	/**
	 * This constructor has been made intentionally made private to provide
	 * pre-defined types only.
	 * 
	 * @param name
	 *            the name of the new resource type
	 * @param path
	 *            the absolute path of the new resource type
	 * @param allowedEextensions
	 *            the allowed extensions set of the new resource type
	 * @param deniedExtensions
	 *            the denied extensions set of the new resource type
	 * @throws IllegalArgumentException
	 *             if both sets are empty
	 * @throws IllegalArgumentException
	 *             if both sets contain extensions
	 */
	private ResourceType(final String name, final String path,
			final Set<String> allowedEextensions,
			final Set<String> deniedExtensions) {
		this.name = name;
		this.path = path;

		if (allowedEextensions.isEmpty() && deniedExtensions.isEmpty())
			throw new IllegalArgumentException(
					"Both sets are empty, one has always to be filled");

		if (!allowedEextensions.isEmpty() && !deniedExtensions.isEmpty())
			throw new IllegalArgumentException(
					"Both sets contain extensions, only one can be filled at the same time");

		this.allowedEextensions = allowedEextensions;
		this.deniedExtensions = deniedExtensions;
	}

	/**
	 * Returns the name of this resource type.
	 * 
	 * @return the name of this resource type
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the absolute path of this resource type. This path is absolute to
	 * the userfiles path. To set this path, see the <a
	 * href="http://java.fckeditor.net/properties.html">configuration</a>.
	 * 
	 * @return the absolute path of this resource type
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns a read-only reference to the allowed extensions set.
	 * 
	 * @return the read-only allowed extensions set of this resource type
	 */
	public Set<String> getAllowedEextensions() {
		return Collections.unmodifiableSet(allowedEextensions);
	}

	/**
	 * Returns a read-only reference to the denied extensions set.
	 * 
	 * @return the read-only denied extensions set of this resource type
	 */
	public Set<String> getDeniedExtensions() {
		return Collections.unmodifiableSet(deniedExtensions);
	}

	/**
	 * Returns the resource type constant with the specified name.
	 * 
	 * @param name
	 *            the name of the constant to return
	 * @return the resource type constant with the specified name
	 * @throws IllegalArgumentException
	 *             if this class has no constant with the specified name
	 * @throws NullPointerException
	 *             if <code>name</code> is null or empty
	 */
	public static ResourceType valueOf(final String name) {
		if (Utils.isEmpty(name))
			throw new NullPointerException("Name is null or empty");

		ResourceType rt = types.get(name);
		if (rt == null)
			throw new IllegalArgumentException("No resource type const " + name);
		return rt;
	}

	/**
	 * Returns <code>true</code> if name represents a valid resource type
	 * constant.
	 * 
	 * @param name
	 *            the resource type to check
	 * @return <code>true</code> if name represents a valid resource type, else
	 *         <code>false</code>
	 */
	public static boolean isValidType(final String name) {
		// HashMap permits null keys, so it will return false in that case
		return types.containsKey(name);
	}

	/**
	 * Returns the resource type constant with the specified name. In contrast
	 * to {@link #valueOf(String)} it returns a null instead of throwing an
	 * exception if a resource type constant was not found.
	 * 
	 * @param name
	 *            the name of the constant to return
	 * @return the resource type constant with the specified name, else
	 *         <code>null</code>
	 */
	public static ResourceType getResourceType(final String name) {
		try {
			return ResourceType.valueOf(name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the resource type constant with the specified name. In contrast
	 * to {@link #getResourceType(String)} it returns {@link #FILE} instead of
	 * returning <code>null</code>.
	 * 
	 * @param name
	 *            the name of the constant to return
	 * @return the resource type constant with the specified name, else
	 *         <code>FILE</code>
	 */
	public static ResourceType getDefaultResourceType(final String name) {
		ResourceType rt = getResourceType(name);
		return rt == null ? FILE : rt;
	}

	/**
	 * Returns <code>true</code> if extension is allowed. Denied extensions set
	 * takes precedence over allowed extensions set, in other words a negative
	 * check is made against denied set and if this fails, allowed set is
	 * checked.
	 * 
	 * @param extension
	 *            the extension to check, empty will fail
	 * @return <code>true</code> if extension is allowed, else
	 *         <code>false</code>
	 */
	public boolean isAllowedExtension(final String extension) {
		if (Utils.isEmpty(extension))
			return false;
		String ext = extension.toLowerCase();
		if (allowedEextensions.isEmpty())
			return !deniedExtensions.contains(ext);
		if (deniedExtensions.isEmpty())
			return allowedEextensions.contains(ext);
		return false;
	}

	/**
	 * This method wraps {@link #isAllowedExtension(String)}. It simply negates
	 * the return value.
	 * 
	 * @deprecated Method will be removed in FCKeditor.Java 2.6, use
	 *             {@link #isDeniedExtension(String)}.
	 * @see #isDeniedExtension(String)
	 * @param extension
	 *            Extension string.
	 * @return <code>true</code> if extension is not fails, else
	 *         <code>false</code>.
	 */
	@Deprecated
	public boolean isNotAllowedExtension(final String extension) {
		return !isAllowedExtension(extension);
	}

	/**
	 * Returns <code>true</code> if extension is denied. This method simply
	 * negates {@link #isAllowedExtension(String)}.
	 * 
	 * @param extension
	 *            the extension to check, empty will fail
	 * @return <code>true</code> if extension is denied, else <code>false</code>
	 */
	public boolean isDeniedExtension(final String extension) {
		return !isAllowedExtension(extension);
	}

	/**
	 * Compares the specified object with this resource type for equality. The
	 * comparison is based on class and name only.
	 * 
	 * @param obj
	 *            Object to be compared with this resource type.
	 * @return <code>true</code> if the specified object is equal to this
	 *         resource type
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || this.getClass() != obj.getClass())
			return false;

		final ResourceType rt = (ResourceType) obj;
		return name.equals(rt.getName());
	}

	/**
	 * Returns the hash code value for this resource type. The hash code equals
	 * the hash code of the name field.
	 * 
	 * @return the hash code value for this resource type
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
