package ule.edi.tree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class BinarySearchTreeTests {

   
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |	∅
	* |  |  |	∅
	* |  |	 ∅
	* |  20
	* |  |  15
	* |  |  |	∅
	* |  |  | 	∅
	* |  |  30
	* |  |  |  	∅
	* |  |  |  	∅
    */	
	private BinarySearchTreeImpl<Integer> ejemplo = null;
	
	
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |  	∅
	* |  |  |  	∅
	* |  | 	 ∅
	* |  20
	* |  |  15
	* |  |  |  12
	* |  |  |  |  	∅
	* |  |  |  |  	∅
	* |  | 	 ∅
  */
	private BinarySearchTreeImpl<Integer> other=null;
	
	@Before
	public void setupBSTs() {
		
			
		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 20, 5, 2, 15, 30);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
		
		
		other =new BinarySearchTreeImpl<Integer>();
		other.insert(10, 20, 5, 2, 15, 12);
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		
	    	}
	
	@Test
	public void testRemoveCountMayor1() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}");
		ejemplo.remove(20);
	    Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}");
	}
	
	@Test
	public void testToString() {
	    Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
	}
	
	@Test
	public void testsizeInstances() {
	    Assert.assertEquals(6,other.size());
	    other.insert(10);
	    other.insert(10);
	    other.insert(10);
	    Assert.assertEquals(6,other.size());
	    Assert.assertEquals(9,other.instancesCount());
	}
	
	@Test
	public void testGetPath() {
	    Assert.assertEquals("{2, ∅, ∅}",other.getSubtreeWithPath("LL").toString());
	}
	
	@Test
	public void testGetRoadUpRight() {
		other.insert(30);
	    Assert.assertEquals("30",other.getRoadUpRight(2, 2, 2).toString());
	}
	
	@Test
	public void testGetRoadUpRightTags() {
		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 5, 2, 7, 20, 15, 12, 30);
		ejemplo.getRoadUpRight(7, 2, 2);
		BinarySearchTreeImpl<Integer> copia = new BinarySearchTreeImpl<Integer>();
		copia = ejemplo.copy();
		copia.getRoadUpRight(7,2,2);
	    Assert.assertEquals("{10 [(road, 3)], {5 [(road, 2)], {2, ∅, ∅}, {7 [(road, 1)], ∅, ∅}}, {20 [(road, 4)], {15, {12, ∅, ∅}, ∅}, {30 [(road, 5)], ∅, ∅}}}",ejemplo.toString());
	    Assert.assertEquals("{10 [(road, 3)], {5 [(road, 2)], {2, ∅, ∅}, {7 [(road, 1)], ∅, ∅}}, {20 [(road, 4)], {15, {12, ∅, ∅}, ∅}, {30 [(road, 5)], ∅, ∅}}}",copia.toString());
	}
	
	@Test
	public void testCopy() {
		BinarySearchTreeImpl<Integer> copia = new BinarySearchTreeImpl<Integer>();
		copia = other.copy();
	    Assert.assertEquals(copia.toString(),other.toString());
	    other.insert(68);
	    Assert.assertFalse(copia.toString().equals(other.toString()));
	}
	
	@Test
	public void testRoadUpRightEnCopy() {
		BinarySearchTreeImpl<Integer> copia = new BinarySearchTreeImpl<Integer>();
		copia = other.copy();
	    Assert.assertEquals(copia.toString(),other.toString());
	    Assert.assertEquals("20",other.getRoadUpRight(2, 2, 1).toString());
	    Assert.assertEquals("20",copia.getRoadUpRight(2, 2, 1).toString());
	}
	
	@Test
	public void testGetSubtreeWithPath() {
	    Assert.assertEquals("" , ejemplo.getPath(10));
	    Assert.assertEquals("LL" , ejemplo.getPath(2));
	    Assert.assertEquals("RL" , ejemplo.getPath(15));
	}
	
	@Test
	public void testIteratorWidth() {
			
			Iterator <Integer> prueba = other.iteratorWidth();
	        String cadena="";
	        while(prueba.hasNext()) {
	            cadena+=prueba.next()+" ";
	        }
	        Assert.assertEquals("10 5 20 2 15 12 ", cadena);

		}
	
	@Test
	public void testIteratorWidthInstances() {
			other.insert(20);
			other.insert(20);
			Iterator <Integer> prueba = other.iteratorWidthInstances();
	        String cadena="";
	        while(prueba.hasNext()) {
	            cadena+=prueba.next()+" ";
	        }
	        Assert.assertEquals("10 5 20 20 20 2 15 12 ", cadena);

		}
	
	@Test
	public void testRemoveCountMayor1HastaVaciar() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {30, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemoveHoja() {
		ejemplo.remove(30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove1Hijo() {
		ejemplo.remove(5);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove2Hijos() {
		ejemplo.remove(10);
		Assert.assertEquals("{15, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void testtagPosDescend() {
			ejemplo = new BinarySearchTreeImpl<Integer>();
			ejemplo.insert(50, 30, 10, 40, 80, 60);
			ejemplo.tagPosDescend();
			Assert.assertEquals("{50 [(descend, 3)], {30 [(descend, 5)], {10 [(descend, 6)], ∅, ∅}, {40 [(descend, 4)], ∅, ∅}}, {80 [(descend, 1)], {60 [(descend, 2)], ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testTagInOrden() {
			ejemplo = new BinarySearchTreeImpl<Integer>();
			ejemplo.insert(30, 10, 5, 2, 20, 15, 12);
			ejemplo.tagInternalInorder();
			Assert.assertEquals("{30 [(internal, 7)], {10 [(internal, 3)], {5 [(internal, 2)], {2, ∅, ∅}, ∅}, {20 [(internal, 6)], {15 [(internal, 5)], {12, ∅, ∅}, ∅}, ∅}}, ∅}",ejemplo.toString());
			Assert.assertEquals(5,ejemplo.tagInternalInorder());
	}
	
	@Test
	public void testTagOnlySon() {
			other.tagOnlySonPreorder();
			Assert.assertEquals("{10, {5, {2 [(onlySon, 3)], ∅, ∅}, ∅}, {20, {15 [(onlySon, 5)], {12 [(onlySon, 6)], ∅, ∅}, ∅}, ∅}}",other.toString());
			Assert.assertEquals(3,other.tagOnlySonPreorder());
	}
	
	@Test
	public void testTagHeightLeafEjemplo() {
			other.tagHeightLeaf();
			other.filterTags("height");
			Assert.assertEquals("{10, {5, {2 [(height, 3)],"+" ∅, ∅}, ∅}, {20, {15, {12 [(height, 4)],"+" ∅, ∅}, ∅}, ∅}}",other.toString());
		}
				
				
		@Test(expected = IllegalArgumentException.class)
		public void testInsertException() {
			Integer i = null;
			other.insert(i);	
		}
		
	
		@Test(expected = IllegalArgumentException.class)
		public void testContainsNull() {
			other.contains(null);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testRemoveNullElement() {
			Integer i = null;
			other.remove(i);
		}
		
		@Test(expected = NoSuchElementException.class)
		public void testRemoveNoSuchElement() {
			other.remove(11);
		}
	}


