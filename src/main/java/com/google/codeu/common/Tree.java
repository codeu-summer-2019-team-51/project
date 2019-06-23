package com.google.codeu.common;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree<E> {
  List<Node<E>> roots;
  HashMap<String, Node<E>> contentToNodes;

  public Tree() {
    roots = new ArrayList<Node<E>>();
    contentToNodes = new HashMap<String, Node<E>>();
  }

  /**
   * Adds a new root node to the tree.
   */
  public void addRoot(String id, E content) {
    Node<E> root = new Node<E>();
    root.setContent(content);
    roots.add(root);
    contentToNodes.put(id, root);
  }

  /**
   * Adds a new node to the tree.
   */
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
    contentToNodes.put(childId, childNode);

    parentNode.addChild(childNode);
  }

  /**
   * Gives a JSON representation of the {@link Tree}.
   */
  public String toJson() {
    if (roots.size() == 0) {
      return "";
    }
    Gson gson = new Gson();
    return gson.toJson(roots);
  }

  private class Node<E> {
    private E content;
    private List<Node<E>> children;

    public Node() {
      content = null;
      children = new ArrayList<Node<E>>();
    }

    public E getContent() {
      return content;
    }

    public void setContent(E content) {
      this.content = content;
    }

    public void addChild(Node<E> child) {
      children.add(child);
    }
  }
}
