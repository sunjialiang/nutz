package org.nutz.mvc;

import org.nutz.ioc.ObjLoader;
import org.nutz.ioc.meta.Obj;

public class MvcMappingLoader implements ObjLoader {

	private ObjLoader loader;

	public MvcMappingLoader(ObjLoader loader) {
		this.loader = loader;
	}

	public Obj load(String name) {
		Obj obj = loader.load(name);
		if (null != obj.getType()) {
			try {
				Class<?> type = Class.forName(obj.getType());
				if (Action.class.isAssignableFrom(type)) {
					obj.setSingleton(false);
				}
			} catch (ClassNotFoundException e) {
			}
		}
		return obj;
	}

	public String[] keys() {
		return loader.keys();
	}

	public boolean hasObj(String name) {
		return loader.hasObj(name);
	}

}