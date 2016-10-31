
public class Moon {

	//Initializing variables
	private String _desciption;
	private UUID _npcId;

	//Constructor
	public Moon(String desciption, UUID npcId) {
		this._desciption = desciption;
		this._npcId = npcId;
	}

	//Getters
	public String getDescription() {
		return desciption;

	public UUID getNpcId() {
		return this._npcId;
	}
}