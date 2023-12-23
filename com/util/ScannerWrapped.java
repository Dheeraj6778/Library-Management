package com.util;

import java.util.Scanner;

public class ScannerWrapped {
    private static Scanner scanner=null;
    private ScannerWrapped(){}
    public static Scanner getScanner(){
        if(scanner==null)
            scanner=new Scanner(System.in);
        return scanner;
    }
}
