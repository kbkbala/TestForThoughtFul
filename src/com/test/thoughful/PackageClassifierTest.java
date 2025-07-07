package com.test.thoughful;

import java.util.function.Predicate;

public class PackageClassifierTest {

	public static void main(String[] args) {
		
		// Test cases
        System.out.println("100x100x100, 25 is : " + sortThePackages(100, 100, 100,25)); // SPECIAL
        System.out.println("100x100x101, 15 is : " + sortThePackages(100, 100, 101,15)); // SPECIAL
        System.out.println("50x50x400, 20 is : " + sortThePackages(50, 50, 400,20)); // REJECTED
        System.out.println("50x50x400, 18 is : " + sortThePackages(50, 50, 100,18)); // STANDARD
        System.out.println("1000x1000x140, 18 is : " + sortThePackages(1000, 1000, 140, 18)); // SPECIAL
        System.out.println("1000x1000x1000, 21 is : " + sortThePackages(1000, 1000, 1000,21)); // REJECTED

	}
	
	private static Package sortThePackages(int width,int height,int length,int mass){
		if(width >0  && height > 0 && length > 0) {			
			int volmune = width * height * length; 
			
			// Predicate to check if a volume is greater then 1000000 cm3,then it is bulky
	        Predicate<Integer> isBulkyVolume = n -> n >= 1000000;	        
	        Predicate<Integer> isNotBulkyVolume = isBulkyVolume.negate();
	        
	        Predicate<Integer> isBulkyDimension = n -> n >= 150;
	        Predicate<Integer> isNotBulkyDim = isBulkyDimension.negate();
	        
	        Predicate<Integer> isHeavy = n -> n >= 20;
	        Predicate<Integer> isNotHeavy = isHeavy.negate();
	        
	        //some useful predicates
	        Predicate<Integer> isNotBulkyOrHeavy = isNotBulkyVolume.and(isNotBulkyDim).and(isNotHeavy);
	        Predicate<Integer> isBulkyOrHeavy = isBulkyVolume.or(isBulkyDimension).or(isHeavy);	       
	        Predicate<Integer> isBulkyAndHeavy = isBulkyVolume.and(isHeavy);
	        
	        //BULKY flags
	        boolean isBulkyVol = isBulkyVolume.test(volmune);
	        boolean isWidthBulky = isBulkyDimension.test(width);
	        boolean isHeightBulky = isBulkyDimension.test(height);
	        boolean isLengthBulky = isBulkyDimension.test(length);
	        //Heavy Flag.
	        boolean isMassHeavy = isHeavy.test(mass);
	        
	        boolean isDimensionHeavy = false; 
	        if(isWidthBulky || isHeightBulky || isLengthBulky) {
	        	isDimensionHeavy = true;
	        }
	        
	        boolean isBulkyFlag = false;
	        if(isBulkyVol  || isDimensionHeavy ) {
	        	isBulkyFlag = true;
	        }
	        
	      
	        if(isBulkyFlag && isDimensionHeavy && isMassHeavy) {//3. REJECTED check: Are both BULKY and HEAVY  (1.1 BULKY = isBulky or isBulkyDimension.)  AND (1.2 HEAVY =  isHeavy)	
	        	return Package.REJECTED;
	        } else if((isBulkyFlag | isDimensionHeavy) | isMassHeavy) {  //2. SPECIAL check: If BULKY or HEAVY.  (1.1 BULKY = isBulky or isBulkyDimension.)  OR (1.2 HEAVY =  isHeavy)
	        	return Package.SPECIAL;
	        } else if(!isBulkyFlag &&  !isDimensionHeavy && !isMassHeavy ) { //1. Standard check: Not BULKY OR Not HEAVY. NOT (1.1 BULKY = isBulky or isBulkyDimension.)  OR NOT (1.2 HEAVY =  isHeavy)
	        	return Package.STANDARD;
	        }
	        
		} else {
			return Package.WRONGDIMENSIONS;
		}		
		
		return Package.UNKNOWNERR;
	}
	
	private enum Package{
		STANDARD, SPECIAL, REJECTED, WRONGDIMENSIONS,UNKNOWNERR;
	}

}
