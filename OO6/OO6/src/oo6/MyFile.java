package oo6;

import java.io.File;

public class MyFile {
	
	private File file;
	private String name;
	private long lastModefied;
	private long size;
	private String path;
	
	public MyFile(File file) {
		this.file = file;
		name = file.getName();
		lastModefied = file.lastModified();
		size = file.length();
		path = file.getParent();
	}
	
	public boolean exists(){
		if (file.exists()) {
			return true;
		}
		else
			return false;
	}
	
	public long getfile_lastmodified(){
		return file.lastModified();
	}
	
	public long getfile_size(){
		return file.length();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + (int) (lastModefied ^ (lastModefied >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyFile other = (MyFile) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (lastModefied != other.lastModefied)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLastModefied() {
		return lastModefied;
	}
	public void setLastModefied(long lastModefied) {
		this.lastModefied = lastModefied;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
