package cc.xpcas.nettysocks.config;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author xp
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@ToString
public class Address {

    private String host;

    private int port;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return port == address.port &&
                Objects.equals(host, address.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
