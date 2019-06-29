package com.google.codeu.common;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree<E> {
  Node<E> root;
  HashMap<String, Node<E>> contentToNodes;
  // Maps node's id to the node in the Tree to optimise insertion

  /**
   * Constructs a new Tree by initialising its attributes.
   */
  public Tree() {
    root = new Node<E>();
    contentToNodes = new HashMap<String, Node<E>>();
    contentToNodes.put("", root);
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
    Gson gson = new Gson();
    return gson.toJson(root.getChildren());
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

    public List<Node<E>> getChildren() {
      return children;
    }

    public void addChild(Node<E> child) {
      children.add(child);
    }
  }
}
