package Server.utils;

public enum ClientState {
	ATTENTE(){
		@Override
		public String toString() {
			return "En attente d'une partie...";
		}
	},
	
	PARTIE(){
		@Override
		public String toString() {
			return "En partie...";
		}
	},
	
	AFK() {
		@Override
		public String toString() {
			return "Ne fait rien...";
		}
	};

public ClientState getState() {
	return this;
	}
}