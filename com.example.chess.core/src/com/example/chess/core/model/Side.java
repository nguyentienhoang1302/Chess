package com.example.chess.core.model;

public enum Side {
	WHITE,
	BLACK;

	public Side opposite() {
		// TODO Auto-generated method stub
		return this == WHITE ? BLACK:WHITE;
	}
}
