package net.caidingke.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.SkuFinder;

import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Sku extends BaseModel {

    public static final SkuFinder find = new SkuFinder();

    private static final long serialVersionUID = 2554909899408910350L;
}
