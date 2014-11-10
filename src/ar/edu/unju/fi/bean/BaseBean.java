package ar.edu.unju.fi.bean;

import ar.edu.unju.fi.services.ServiceFacade;
import ar.edu.unju.fi.services.SpringUtil;

public class BaseBean {
	ServiceFacade service;

	public BaseBean() {
		service = (ServiceFacade) SpringUtil.getBean("serviceFacade");
	}

	public ServiceFacade getService() {
		if (service == null)
			service = (ServiceFacade) SpringUtil.getBean("serviceFacade");
		return service;
	}

	public void setService(ServiceFacade service) {
		this.service = service;
	}

}
