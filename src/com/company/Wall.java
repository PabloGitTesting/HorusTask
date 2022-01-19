package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Wall implements Structure {

    List<Block> listOfBlocks = new ArrayList<>();

    private class MyBlock implements Block {
        private final String color;
        private final String material;

        public MyBlock(String color, String material) {
            this.color = color;
            this.material = material;
        }

        @Override
        public String getColor() {
            return this.color;
        }

        @Override
        public String getMaterial() {
            return this.material;
        }
    }

    private class MyCBlock implements CompositeBlock {
        private final String color;
        private final String material;
        private final List<Block> partsOfBlock;

        public MyCBlock(String color, String material, List<Block> partsOfBlock) {
            this.color = color;
            this.material = material;
            this.partsOfBlock = partsOfBlock;
        }

        @Override
        public List<Block> getBlocks() {
            return partsOfBlock;
        }

        @Override
        public String getColor() {
            return this.color;
        }

        @Override
        public String getMaterial() {
            return this.material;
        }
    }

    public boolean addNewBlock(String color, String material) {
        if (validateBlockData(color, material)){
            listOfBlocks.add(new MyBlock(color.toLowerCase(), material.toLowerCase()));
            return true;
        }
        return false;
    }

    public boolean addNewCBlock(String color, String material, List<Block> bl) {
        if (validateBlockData(color, material) && validateListData(bl)) {
            listOfBlocks.add(new MyCBlock(color.toLowerCase(), material.toLowerCase(), bl));
            return true;
        }
        return false;
    }

    private boolean validateBlockData(String color, String material) {
        return (color != null && color.length() > 0) && (material != null && material.length() > 0);
    }

    private boolean validateListData(List<Block> l) {
        for (Block b : l) {
            if (!validateBlockData(b.getColor(), b.getMaterial())) {
                return false;
            }
        }
        return true;
    }

    // zwraca dowolny element o podanym kolorze
    @Override
    public Optional findBlockByColor(String color) {
        Optional<Block> first = listOfBlocks.stream().filter(element -> element.getColor().equals(color.toLowerCase())).findAny();
        return first;
    }

    // zwraca wszystkie elementy z danego materiału
    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return new ArrayList<Block>(listOfBlocks.stream().filter(elements -> elements.getMaterial().equals(material.toLowerCase()))
                .collect(Collectors.toList()));
    }

    //zwraca liczbę wszystkich elementów tworzących strukturę
    @Override
    public int count() {
        int counter = 0;
        for (Block b : listOfBlocks) {
            if (b.getClass() == MyCBlock.class) {
                counter += ((MyCBlock) b).getBlocks().size();
            } else
                counter++;
        }
        return counter;
    }


}
