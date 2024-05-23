package nz.ac.auckland.se281;

/** Class to represent each country with name, continent and tax filed. */
public class Country {
  private String name;
  private String continent;
  private String tax;

  /**
   * Constructor to set up the fields for the country class.
   *
   * @param name is the name of the country.
   * @param continent is the continent of the country.
   * @param tax is the tax to enter that country.
   */
  public Country(String name, String continent, String tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  /**
   * Overriden code to define hash code only using name so if two countries have same name, they are
   * considered the same.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /** Overriden code to say any country with the same name is equal. */
  @Override
  public boolean equals(Object obj) {
    // checks if same object
    if (this == obj) return true;

    // checks if object is not null
    if (obj == null) return false;

    // checks if same class
    if (getClass() != obj.getClass()) return false;

    // typecast to same class
    Country other = (Country) obj;

    // checks if they have same name
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  /**
   * Getter method for name of country.
   *
   * @return name of country.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method for the continent to country is in.
   *
   * @return the conitinent of country.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Getter method for the tax to enter that country.
   *
   * @return the entering tax.
   */
  public String getTax() {
    return tax;
  }
}
