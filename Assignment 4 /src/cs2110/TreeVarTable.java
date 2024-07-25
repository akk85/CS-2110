package cs2110;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the VarTable interface using a binary search tree (BST). This class provides
 * methods to associate variable names with numeric values, retrieve values associated with variable
 * names, remove associations, check for the existence of an association, get the number of
 * associations, and retrieve the names of all associated variables.
 */
public class TreeVarTable implements VarTable {

    private TreeNode root;

    private static class TreeNode {

        String key;
        double value;
        TreeNode left;
        TreeNode right;

        TreeNode(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Retrieves the value associated with the given variable name. Returns the value associated
     * with the specified variable and throws UnboundVariableException if the variable is not found
     * in the table.
     */
    @Override
    public double get(String var) throws UnboundVariableException {
        TreeNode node = search(root, var);
        if (node == null) {
            throw new UnboundVariableException("Variable not found");
        }
        return node.value;
    }

    /**
     * Associates a given value with a specified variable name. If the variable already exists in
     * the table, its value is updated with the new value.
     */
    @Override
    public void set(String var, double value) {
        root = insert(root, var, value);
    }

    /**
     * Removes the association of a given variable name from the table. If the variable is not
     * found, the method will not have any effect.
     */
    @Override
    public void unset(String name) {
        root = delete(root, name);
    }

    /**
     * Checks if a variable name is associated with a value in the table.Returns true if the
     * variable is associated with a value, false otherwise.
     */
    @Override
    public boolean contains(String name) {
        return search(root, name) != null;
    }


    /**
     * Returns the number of variable associations present in the table.
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * Returns a set containing the names of all variables that are associated with a value in the
     * table.
     */
    @Override
    public Set<String> names() {
        Set<String> nameSet = new HashSet<>();
        collectNames(root, nameSet);
        return nameSet;
    }

    /**
     * Helper method to get names of all variables in the table
     */
    private void collectNames(TreeNode node, Set<String> nameSet) {
        if (node == null) {
            return;
        }

        nameSet.add(node.key);
        collectNames(node.left, nameSet);
        collectNames(node.right, nameSet);
    }

    /**
     * Helper method that inserts a node with a value in the table.
     */
    private TreeNode insert(TreeNode root, String key, double value) {
        if (root == null) {
            return new TreeNode(key, value);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = insert(root.left, key, value);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insert(root.right, key, value);
        } else {  // key is already present
            root.value = value;
        }
        return root;
    }

    /**
     * Helper method that returns node being searched for. Compares values of nodes and * if value
     * of node equals that of key, returns this node.
     */
    private TreeNode search(TreeNode root, String key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return search(root.left, key);
        } else if (cmp > 0) {
            return search(root.right, key);
        } else {
            return root;
        }
    }

    /**
     * Helper method that deletes a node whose value corresponds to that of key
     */
    private TreeNode delete(TreeNode root, String key) {
        if (root == null) {
            return root;
        }

        if (key.compareTo(root.key) < 0) {
            root.left = delete(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = delete(root.right, key);
        } else {
            if ((root.left == null) || (root.right == null)) {
                TreeNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                TreeNode temp = minValueNode(root.right);
                root.key = temp.key;
                root.value = temp.value;
                root.right = delete(root.right, temp.key);
            }
        }

        return root;
    }

    /**
     * Helper method that returns the node with the smallest key in the subtree. If the subtree is
     * empty, returns the root.
     */
    private TreeNode minValueNode(TreeNode root) {
        TreeNode current = root;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    /**
     * Helper method that recursively calculates and returns the number of nodes in the subtree
     * rooted at the given node. Returns the number of nodes in the subtree. If the node is null,
     * returns 0.
     */
    private int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

}
