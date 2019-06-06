package at.fh.swenga.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.dao.DocumentRepository;
import at.fh.swenga.dao.SongRepository;
import at.fh.swenga.model.DocumentModel;
import at.fh.swenga.model.SongModel;

import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class SongController {

	@Autowired
	SongRepository songRepository;
	

	@Autowired
	DocumentRepository documentRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<SongModel> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		return "index";
	}

	@RequestMapping("/fill")
	@Transactional
	public String fillData(Model model) {

		DataFactory df = new DataFactory();

		
		for(int i=0;i<100;i++) {
			
			SongModel songModel = new SongModel(
					df.getRandomWord(),
					df.getName(),
					df.getRandomWord(),
					df.getBirthDate());
			songRepository.save(songModel);
		}
		
		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		songRepository.deleteById(id);

		return "forward:list";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam("id") int songId) {
		model.addAttribute("songId", songId);
		return "uploadFile";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") int songId,
			@RequestParam("myFile") MultipartFile file) {
 
		try {
 
			Optional<SongModel> songOptional = songRepository.findById(songId);
			if (!songOptional.isPresent()) throw new IllegalArgumentException("No song with this id found: ID:  "+songId);
 
			SongModel song = songOptional.get();
 
			if (song.getCoverArt() != null) {
				documentRepository.delete(song.getCoverArt());
				song.setCoverArt(null);
			}
			
			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			song.setCoverArt(document);
			songRepository.save(song);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
		}
 
		return "forward:/list";
	}

	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {
 
		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent()) throw new IllegalArgumentException("No document with this id found: ID: "+documentId);
 
		DocumentModel doc = docOpt.get();
 
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
	
}
