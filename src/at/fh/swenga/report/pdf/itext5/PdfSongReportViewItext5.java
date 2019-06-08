package at.fh.swenga.report.pdf.itext5;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import at.fh.swenga.model.SongModel;

public class PdfSongReportViewItext5 extends AbstractIText5PdfView {

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
		//table.setWidths(new float[] { 1.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
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
