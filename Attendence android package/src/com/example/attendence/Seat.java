package com.example.attendence;


public class Seat {

    public String seatno;
    public int img;

    public String getSeatno() {
        return seatno;
    }

    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Seat(String seatno, int img) {
        this.seatno = seatno;
        this.img = img;
    }
    
    public int getallimg(){
    	return img;
    	
    }

}
