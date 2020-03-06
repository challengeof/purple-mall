package net.caidingke.business.controller;

import net.caidingke.business.service.WechatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bowen
 */
@RestController
@RequestMapping(value = "/wechat")
public class WechatController {

    private final WechatService wechatService;

    public WechatController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    @RequestMapping(value = "/receiveWechatMpMessage", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiveMpMessage(HttpServletRequest request) throws Exception {
        return wechatService.receiveMpMessage(request);
    }
}
