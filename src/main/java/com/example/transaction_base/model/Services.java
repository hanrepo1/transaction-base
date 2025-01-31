package com.example.transaction_base.model;

public class Services {

    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private int serviceTariff;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public Integer getServiceTariff() {
        return serviceTariff;
    }

    public void setServiceTariff(Integer serviceTariff) {
        this.serviceTariff = serviceTariff;
    }
}
