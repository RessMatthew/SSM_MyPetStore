package org.csu.mypetstore.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

  private static final long serialVersionUID = -7492639752670189553L;
  private String productId;
  private String categoryId;
  private String name;
  private String description;


}
