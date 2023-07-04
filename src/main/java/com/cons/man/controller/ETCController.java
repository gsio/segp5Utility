package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.BeaconService;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.HoleService;
import com.cons.man.services.NFCService;
import com.cons.man.services.UserService;
import com.cons.man.services.WorkerService;
import com.cons.man.services.seg.SegService;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller(value="ETCController")
public class ETCController {

}