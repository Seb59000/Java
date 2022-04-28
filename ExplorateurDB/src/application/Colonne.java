package application;

public class Colonne {

	private String _type;
	private String _nom;

	public Colonne(String nom, String type) {
		_type = type;
		_nom = nom;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_nom() {
		return _nom;
	}

	public void set_nom(String _nom) {
		this._nom = _nom;
	}
}
