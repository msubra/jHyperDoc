import java.util.Vector;

public class GateList extends Vector {
	public GateList() {
		super();
	}
	
	public GateList( int index ){
		super(index);
	}
	
	public synchronized Gate getGate( int index ){
		return (Gate)super.get(index);
	}
}
