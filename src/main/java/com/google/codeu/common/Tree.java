package com.google.codeu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

public class Tree<E> {
  Node<E> root;
  HashMap<String, Node<E>> contentToNodes;

  public Tree() {
    contentToNodes = new HashMap<String, Node<E>>();
  }

  public void add(String childId, E content, String parentId) {
    Node<E> childNode = contentToNodes.get(childId);
    Node<E> parentNode = contentToNodes.get(parentId);

    // Handle the case where the parent has not been added yet
    if (parentNode == null) {
      parentNode = new Node<E>();
      contentToNodes.put(parentId, parentNode);
    }

    // Handle the case where the child's child has not been added yet
    if (childNode == null) {
      childNode = new Node<E>();
    }
    childNode.setContent(content);
    childNode.setParent(parentNode);
    contentToNodes.put(childId, childNode);

    parentNode.addChild(childNode);
  }

  public E getRoot() {
    return root.getContent();
  }

  public String toJson() {
    if (root == null) {
      return "";
    }

    return root.toJson();
  }
}

class Node<E> {
  private E content;
  private Node<E> parent;
  private List<Node<E>> children;

  public Node() {
    this.content = null;
    this.parent = new Node<E>();
    this.children = new ArrayList<Node<E>>();
  }

  public E getContent() {
    return content;
  }

  public void setContent(E content) {
    this.content = content;
  }

  public void setParent(Node<E> parent) {
    this.parent = parent;
  }

  public void addChild(Node<E> child) {
    this.children.add(child);
  }

  public String toJson() {
    Gson gson = new Gson();
    String contentJson = gson.toJson(content);
    contentJson = contentJson.substring(0, contentJson.length()-1);

    List<String> childrenJson = new ArrayList<String>();
    for (Node<E> child : children) {
      childrenJson.add(child.toJson());
    }

    contentJson += String.format(", children: %s}", gson.toJson(childrenJson));
    return contentJson;
  }
}
