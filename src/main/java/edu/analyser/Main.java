package edu.analyser;

import com.github.katjahahn.parser.PEData;
import com.github.katjahahn.parser.PELoader;
import com.github.katjahahn.parser.sections.SectionLoader;
import com.github.katjahahn.parser.sections.idata.ImportDLL;
import com.github.katjahahn.parser.sections.idata.ImportSection;
import com.github.katjahahn.parser.sections.idata.NameImport;
import com.github.katjahahn.parser.sections.idata.OrdinalImport;
import com.github.katjahahn.tools.ReportCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {


        File file = new File("/Users/aleksandr/ЗОС:ОКЭ/PrgEx/EMLViewer.exe");
        PEData data = PELoader.loadPE(file);
        // ReportCreator reporter = new ReportCreator(data);
        SectionLoader loader = new SectionLoader(data);
        ImportSection idata = loader.loadImportSection();
        List<ImportDLL> list = idata.getImports();
        for (ImportDLL dll : list) {
            System.out.println("Imports from " + dll.getName());
            for (NameImport nameImport : dll.getNameImports()) {
                System.out.print("Name: " + nameImport.getName());
                System.out.print(" Hint: " + nameImport.getHint());
                System.out.println(" RVA: " + nameImport.getRVA());
            }
            for (OrdinalImport ordImport : dll.getOrdinalImports()) {
                System.out.println("Ordinal: " + ordImport.getOrdinal());
            }
            System.out.println();
        }
    }
}
