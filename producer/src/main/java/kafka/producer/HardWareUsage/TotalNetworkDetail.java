package kafka.producer.HardWareUsage;

public class TotalNetworkDetail {
    private int IN;
    private int OUT;
    
    public TotalNetworkDetail() {
        // 기본 생성자
    }
    
    public TotalNetworkDetail(int IN, int OUT) {
        this.IN = IN;
        this.OUT = OUT;
    }
    
    public int getIN() {
        return IN;
    }
    
    public TotalNetworkDetail setIN(int IN) {
        this.IN = IN;
        return this;
    }
    
    public int getOUT() {
        return OUT;
    }
    
    public TotalNetworkDetail setOUT(int OUT) {
        this.OUT = OUT;
        return this;
    }
    
    @Override
    public String toString() {
        return "TotalNetworkDetail{" +
                "IN=" + IN +
                ", OUT=" + OUT +
                '}';
    }
}

