package com.skilldistillery.filmquery.entities;

public class VHSTape {
	private int id;
	private int filmId;
	private String mediaCondition;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public String getMediaCondition() {
		return mediaCondition;
	}
	public void setMediaCondition(String mediaCondition) {
		this.mediaCondition = mediaCondition;
	}
	public VHSTape(int id, int filmId, String mediaCondition) {
		super();
		this.id = id;
		this.filmId = filmId;
		this.mediaCondition = mediaCondition;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + filmId;
		result = prime * result + id;
		result = prime * result + ((mediaCondition == null) ? 0 : mediaCondition.hashCode());
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
		VHSTape other = (VHSTape) obj;
		if (filmId != other.filmId)
			return false;
		if (id != other.id)
			return false;
		if (mediaCondition == null) {
			if (other.mediaCondition != null)
				return false;
		} else if (!mediaCondition.equals(other.mediaCondition))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VHSTape [id=").append(id).append(", filmId=").append(filmId).append(", mediaCondition=")
				.append(mediaCondition).append("]");
		return builder.toString();
	}
	
	
}
