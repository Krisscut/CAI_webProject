package com.enib.cai.web.services;

public interface Files {

  public byte[] get(String name);

  public void put(byte[] data, String name);
}
