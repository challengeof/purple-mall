package net.caidingke.business.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bowen
 */
@Getter
@Setter
public class LotteryParams {
    private List<Integer> candidate;

    private int prizeQuantity;

    private boolean fair;
}
