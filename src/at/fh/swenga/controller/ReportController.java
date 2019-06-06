package at.fh.swenga.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.SongRepository;
import at.fh.swenga.model.SongModel;

import org.springframework.web.bind.annotation.ExceptionHandler;


@Controller
public class ReportController {
	@Autowired
	SongRepository songRepository;

	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage templateMessage;

	@RequestMapping(value = { "/report" })
	public String report(Model model, @RequestParam(required = false) String excel,
			@RequestParam(required = false) String pdf, @RequestParam(required = false) String mail,
			@RequestParam(name = "songId", required = false) List<Integer> songIds) {

		if (CollectionUtils.isEmpty(songIds)) {
			model.addAttribute("errorMessage", "No songs selected!");
			return "forward:/list";
		}

		List<SongModel> songs = songRepository.findAllById(songIds);
		model.addAttribute("songs", songs);

		if (StringUtils.isNoneEmpty(excel)) {
			return "excelReport";
		} else if (StringUtils.isNoneEmpty(pdf)) {
			return "pdfReportV5";
		} 
		else {
			return "forward:/list";
		}
	}


	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}

}
