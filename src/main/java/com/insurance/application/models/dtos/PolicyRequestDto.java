package com.insurance.application.models.dtos;

public class PolicyRequestDto {

    private int policyId;
    private int policyApprovalStatus;
    private double policyPrice;
    private String startDate;
    private String startTime;
    private String vechicleModel;
    private String vechicleBrand;
    private String vehicleRegDate;
    private String vehicleCubicCapacity;

    // Empty constructor
    public PolicyRequestDto() {
    }


    //Getters and Setters

    public int getPolicyId() {
        return policyId;
    }

    public int getPolicyApprovalStatus() {
        return policyApprovalStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getVechicleModel() {
        return vechicleModel;
    }

    public String getVechicleBrand() {
        return vechicleBrand;
    }

    public String getVehicleRegDate() {
        return vehicleRegDate;
    }

    public double getPolicyPrice() {
        return policyPrice;
    }

    public String getVehicleCubicCapacity() {
        return vehicleCubicCapacity;
    }

    public void setVehicleCubicCapacity(String vehicleCubicCapacity) {
        this.vehicleCubicCapacity = vehicleCubicCapacity;
    }

    public void setPolicyPrice(double policyPrice) {
        this.policyPrice = policyPrice;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public void setPolicyApprovalStatus(int policyApprovalStatus) {
        this.policyApprovalStatus = policyApprovalStatus;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setVechicleModel(String vechicleModel) {
        this.vechicleModel = vechicleModel;
    }

    public void setVechicleBrand(String vechicleBrand) {
        this.vechicleBrand = vechicleBrand;
    }

    public void setVehicleRegDate(String vehicleRegDate) {
        this.vehicleRegDate = vehicleRegDate;
    }
}
