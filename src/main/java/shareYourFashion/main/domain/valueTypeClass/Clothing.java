package shareYourFashion.main.domain.valueTypeClass;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Clothing {
    private String outers;
    private String top;
    private String pants;
    private String shoes;
    private String accessory;
    private String bag;
    private String socks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return Objects.equals(outers, clothing.outers) && Objects.equals(top, clothing.top) && Objects.equals(pants, clothing.pants) && Objects.equals(shoes, clothing.shoes) && Objects.equals(accessory, clothing.accessory) && Objects.equals(bag, clothing.bag) && Objects.equals(socks, clothing.socks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outers, top, pants, shoes, accessory, bag, socks);
    }
}
