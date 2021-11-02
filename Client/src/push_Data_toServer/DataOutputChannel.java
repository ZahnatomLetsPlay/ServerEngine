package push_Data_toServer;

public class DataOutputChannel {

	String data;

	//@formatter:off
	public DataOutputChannel( ) {}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getProcessedData() {
		if(!this.data.isBlank()) {
			return this.data;
		}
		return "noData";
	}
}
