package net.caidingke.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.SkuFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Sku extends BaseModel {

    public static final SkuFinder find = new SkuFinder();

    private static final long serialVersionUID = 2554909899408910350L;
}
