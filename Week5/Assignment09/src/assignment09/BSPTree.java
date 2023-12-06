package assignment09;

import java.util.ArrayList;
/**
 * Binary Space Partitioning (BSP) Tree implementation.
 * This tree structure organizes line segments in a spatial hierarchy.
 */
    public class BSPTree {

            class Node {
                Segment segment;
                Node left;
                Node right;
                Node(Segment value) {
                    segment = value;
                    left = null;
                    right = null;
                }
            }
        /**
         * Inner Node class representing a node in the BSP tree.
         */
        private Node root = null;
        /**
         * Default constructor for an empty BSP tree.
         */
        public BSPTree(){
            root = null;
        }
        /**
         * Constructor for creating a BSP tree from a list of segments.
         *
         * @param segments ArrayList of segments to construct the tree.
         */
        public BSPTree(ArrayList<Segment> segments){
            if(segments != null && !segments.isEmpty()){
                root = buildBSPTree(segments);
            }
        }

        /**
         * Builds the BSP tree recursively from a list of segments.
         *
         * @param segments ArrayList of segments for constructing the tree.
         * @return The root node of the BSP tree.
         */
        private Node buildBSPTree(ArrayList<Segment> segments){
            if(segments.isEmpty()) {
                return null;
            }
            int index = (int) (Math.random() * segments.size());
            Segment randomSegment = segments.get(index);
            Node node = new Node(randomSegment);
            segments.remove(index);
            ArrayList<Segment> leftSegments = new ArrayList<>();
            ArrayList<Segment> rightSegments = new ArrayList<>();
            for(Segment loopSegment : segments) {
                int side = node.segment.whichSide(loopSegment);
                if (side < 0) {
                    leftSegments.add(loopSegment);
                } else if (side > 0) {
                    rightSegments.add(loopSegment);
                } else {
                    Segment[] split = node.segment.split(loopSegment);
                    leftSegments.add(split[0]);
                    rightSegments.add(split[1]);
                }
            }
            node.left = buildBSPTree(leftSegments);
            node.right = buildBSPTree(rightSegments);
            return node;
        }
        /**
         * Inserts a segment into the BSP tree.
         *
         * @param segment The segment to be inserted.
         */
        public void insert(Segment segment){
            if(root == null){
                root = new Node(segment);
            }else {
                recursiveInsert(root, segment);
            }
        }
        /**
         * Recursive method for inserting a segment into the tree.
         *
         * @param node    The current node being evaluated during insertion.
         * @param segment The segment to be inserted.
         */
        private void recursiveInsert(Node node, Segment segment) {
            if (node == null) {
                node = new Node(segment);
            }
            int whichSide = segment.whichSide(node.segment);
            if (whichSide > 0) {
                if (node.left == null) {
                    node.left = new Node(segment);
                }else {
                    recursiveInsert(node.left, segment);
                }
            }
            if (whichSide < 0) {
                if (node.right == null) {
                    node.right = new Node(segment);
                } else {
                    recursiveInsert(node.right, segment);
                }
            } else {
                Segment[] splitSegmentArray = segment.split(segment);
                if (node.left == null) {
                    node.left = new Node(splitSegmentArray[0]);
                } else {
                    recursiveInsert(node.left, splitSegmentArray[0]);
                }
                if (node.right == null) {
                    node.right = new Node(splitSegmentArray[1]);
                } else {
                    recursiveInsert(node.right, splitSegmentArray[1]);
                }
            }
        }
        /**
         * Traverses the tree in "far to near" order relative to a point (x, y).
         *
         * @param x        The x-coordinate of the point.
         * @param y        The y-coordinate of the point.
         * @param callback Callback function to handle each segment encountered in the traversal.
         */
        public void traverseFarToNear(double x, double y, SegmentCallback callback){
            traverseRecursive(root, x, y, callback);
        }
        /**
         * Recursive method for traversing the tree.
         *
         * @param node     The current node being evaluated during traversal.
         * @param x        The x-coordinate of the point.
         * @param y        The y-coordinate of the point.
         * @param callback Callback function to handle each segment encountered in the traversal.
         */
        private void traverseRecursive(Node node, double x, double y, SegmentCallback callback){
            if(node == null) {
                return;
            }
            int side = node.segment.whichSidePoint(x, y);
            if (side < 0) {
                traverseRecursive(node.right, x, y, callback);
                callback.callback(node.segment);
                traverseRecursive(node.left, x, y, callback);
            } else {
                traverseRecursive(node.left, x, y, callback);
                callback.callback(node.segment);
                traverseRecursive(node.right, x, y, callback);
            }
        }
        /**
         * Finds any segment in the tree that intersects with the given query segment.
         *
         * @param query The segment to check for collisions.
         * @return Any segment in the tree that intersects with the query segment.
         */
        public Segment collision (Segment query){
            return recursiveCollision(root, query);
        }
        /**
         * Recursive method to check collisions between segments in the tree and the query segment.
         *
         * @param node  The current node being evaluated for collision.
         * @param query The segment to check for collisions.
         * @return The intersecting segment found in the tree with the query segment.
         */
        private Segment recursiveCollision(Node node, Segment query){
            if(node == null){
                return null;
            }
            int side = query.whichSide(node.segment);
            if(side < 0){
                return recursiveCollision(node.right,query);
            }
            else if (side > 0){
                return recursiveCollision(node.left,query);
            }
            else {
                if (query.intersects(node.segment)) {
                    return node.segment;
                }
                else{
                    Segment left = recursiveCollision(node.right, query);
                    if(left != null){
                        return left;
                    }else{
                        return recursiveCollision(node.left, query);
                    }
                }
            }
        }
    }
