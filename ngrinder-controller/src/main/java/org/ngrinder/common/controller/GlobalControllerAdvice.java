package org.ngrinder.common.controller;

import org.ngrinder.infra.config.Config;
import org.ngrinder.operation.service.AnnouncementService;
import org.ngrinder.region.service.RegionService;
import org.ngrinder.user.service.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.ngrinder.common.util.NoOp.noOp;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Value("${ngrinder.version}")
	private String version;

	@Autowired
	private UserContext userContext;

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private Config config;

	@ModelAttribute
    public void globalAttributes(Model model) {
		model.addAttribute("version", version);
		model.addAttribute("clustered", config.isClustered());
		model.addAttribute("visibleRegions", regionService.getAllVisibleRegionNames());
		model.addAttribute("helpUrl", config.getHelpUrl());
		model.addAttribute("hasNewAnnouncement", announcementService.isNew());
		try {
			model.addAttribute("currentUser", userContext.getCurrentUser());
		} catch (Exception e) {
			noOp();
		}
	}
}