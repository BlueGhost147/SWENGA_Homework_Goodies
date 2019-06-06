package at.fh.swenga.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Song")

public class SongModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String songName;
	
	private String artist;
	
	@Column(nullable = false, length = 30)
	private String album;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DocumentModel coverArt;

	
	public SongModel() {
		super();
	}
	
	public SongModel(String songName, String artist, String album, Date releaseDate) {
		super();
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.releaseDate = releaseDate;
	}

	public SongModel(int id, String songName, String artist, String album, Date releaseDate) {
		super();
		this.id = id;
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.releaseDate = releaseDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongModel other = (SongModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return songName + " - " + artist;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public DocumentModel getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(DocumentModel coverArt) {
		this.coverArt = coverArt;
	}
	
	
	
}