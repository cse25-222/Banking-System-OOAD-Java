package bw.ac.bank.model;

public class Branch {
    private String branchCode;
    private String branchName;
    private String location;

    public Branch(String branchCode, String branchName, String location) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.location = location;
    }

    public String getBranchCode() { return branchCode; }
    public String getBranchName() { return branchName; }
    public String getLocation() { return location; }

    @Override
    public String toString() {
        return branchName + " (" + branchCode + ") - " + location;
    }
}