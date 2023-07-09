package kafka.producer.HardWareUsage;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class HardWareUsageDAO {

    /*
    TotalCpuDetail
        CPU HardWare Total Usage
        This is divided into [User, System]
    TotalMemDetail
        MEM HardWare Total Usage
        This is divided into [Used, Unused]
    TotalDiskDetail
        Disk is HardWare Total Usage
        This is divided into [Read, Write]
    TopProcessDetail
        This is COMMAND TOP result where ranked top 5
        This is consist of [PID, COMMAND, CPUusag˜e, Time, Mem, State]
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
     */



    private String EC2Number;
    private TotalCpuDetail CPU;
    private TotalMemDetail MEM;
    private TotalDiskDetail DISK;
    private TotalNetworkDetail NETWORK;
    private List<TopProcessDetail> TopRateProcess;

    public HardWareUsageDAO() {
        this.TopRateProcess = new ArrayList<TopProcessDetail>();
    }

    public HardWareUsageDAO(String EC2Number, TotalCpuDetail CPU, TotalMemDetail MEM, TotalDiskDetail DISK, TotalNetworkDetail NETWORK, List<TopProcessDetail> topRateProcess) {
        this.EC2Number = EC2Number;
        this.CPU = CPU;
        this.MEM = MEM;
        this.DISK = DISK;
        this.NETWORK = NETWORK;
        TopRateProcess = topRateProcess;
    }

    public String getEC2Number() {
        return EC2Number;
    }

    public HardWareUsageDAO setEC2Number(String EC2Number) {
        this.EC2Number = EC2Number;
        return this;
    }

    public TotalCpuDetail getCPU() {
        return CPU;
    }

    public HardWareUsageDAO setCPU(TotalCpuDetail CPU) {
        this.CPU = CPU;
        return this;
    }

    public TotalMemDetail getMEM() {
        return MEM;
    }

    public HardWareUsageDAO setMEM(TotalMemDetail MEM) {
        this.MEM = MEM;
        return this;
    }

    public TotalDiskDetail getDISK() {
        return DISK;
    }

    public TotalNetworkDetail getNETWORK(){
        return NETWORK;
    }
    public HardWareUsageDAO setNETWORK(TotalNetworkDetail NETWORK){
        this.NETWORK = NETWORK;
        return this;
    }

    public HardWareUsageDAO setDISK(TotalDiskDetail DISK) {
        this.DISK = DISK;
        return this;
    }

    public List<TopProcessDetail> getTopRateProcess() {
        return TopRateProcess;
    }

    public HardWareUsageDAO setTopRateProcess(List<TopProcessDetail> topRateProcess) {
        TopRateProcess = topRateProcess;
        return this;
    }

    public void addTopRateProcess(TopProcessDetail tpd) {
        this.TopRateProcess.add(tpd);
    }

    @Override
    public String toString() {
        return "HardWareUsageDAO{" +
                "EC2Number='" + EC2Number + '\'' +
                ", CPU=" + CPU +
                ", MEM=" + MEM +
                ", DISK=" + DISK +
                ", NETWOKR=" + NETWORK +
                ", TopRateProcess=" + TopRateProcess +
                '}';
    }
}
