class Address {
	def public final List streets;
	def public final city;
	def public final state;
	def public final zip;

	def Address(streets, city, state, zip) {
		this.streets = streets;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	def getStreets() {
		Collections.unmodifiableList(streets);
	}
}
