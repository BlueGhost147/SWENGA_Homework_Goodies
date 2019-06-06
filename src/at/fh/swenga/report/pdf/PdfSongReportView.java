package at.fh.swenga.report.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import at.fh.swenga.model.SongModel;

public class PdfSongReportView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		// change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");
 
		List<SongModel> songs = (List<SongModel>) model.get("songs");
 
		document.add(new Paragraph("Song list"));
 
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 2.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);
 
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
 
		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
 
		// write table header
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
 
		cell.setPhrase(new Phrase("Songname", font));
		table.addCell(cell);
 
		cell.setPhrase(new Phrase("Artist", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Album", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Release date", font));
		table.addCell(cell);
 
		// write table row data
		for (SongModel song : songs) {
			table.addCell(song.getId() + "");
			table.addCell(song.getSongName());
			table.addCell(song.getArtist());
			table.addCell(song.getAlbum());
			table.addCell(song.getReleaseDate().toString());
		}
 
		document.add(table);
	}

}
