package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.CollectionUtils;

public class ArvoreArquivo implements Serializable, Comparable<ArvoreArquivo> {

  private static final long serialVersionUID = 1L;

  private String name;

  private String path;

  private ArvoreArquivo parent;

  private List<ArvoreArquivo> children;

  public ArvoreArquivo() {

  }

  public ArvoreArquivo(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @JsonIgnore
  public ArvoreArquivo getParent() {
    return parent;
  }

  public void setParent(ArvoreArquivo parent) {
    this.parent = parent;
  }

  public List<ArvoreArquivo> getChildren() {
    return children;
  }

  public void setChildren(Collection<ArvoreArquivo> children) {
    this.children = new ArrayList<ArvoreArquivo>(children);
  }

  public void addChildren(ArvoreArquivo component) {

    if (this.children == null) {
      this.children = new ArrayList<ArvoreArquivo>();
    }

    this.children.add(component);
  }

  @Override
  public String toString() {
    return name;
  }
  
  @Override
  public int compareTo(ArvoreArquivo other) {
    
    if( !CollectionUtils.isEmpty( children ) && CollectionUtils.isEmpty(other.children) )
      return -1;
    else if( CollectionUtils.isEmpty( children ) == CollectionUtils.isEmpty(other.children) )
      return name.compareTo( other.name );
    else
      return 1;
    
  }
  
}