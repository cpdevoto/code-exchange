package heightweight;

import static heightweight.Die.D10;
import static heightweight.Die.D12;
import static heightweight.Die.D4;
import static heightweight.Die.D6;
import static heightweight.Die.D8;

public enum Race {
  // @formatter:off
  AASIMAR("Aasimar", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 
  
  BUGBEAR("Bugbear", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(6, 0))
      .withBaseWeight(new Weight(200))
      .withSizeModifier(new Dice(2, D12))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  CENTAUR("Centaur", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(6, 0))
      .withBaseWeight(new Weight(600))
      .withSizeModifier(new Dice(1, D10))
      .withWeightMultiplier(new Dice(2, D12))
      .build()), 
  
  CHANGELING("Changeling", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 1))
      .withBaseWeight(new Weight(115))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 
  
  DRAGONBORN("Dragonborn", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 6))
      .withBaseWeight(new Weight(175))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  DWARF_HILL("Dwarf, Hill", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(3, 8))
      .withBaseWeight(new Weight(115))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  DWARF_MOUNTAIN("Dwarf, Mountain", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 0))
      .withBaseWeight(new Weight(130))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  ELF_HIGH("Elf, High", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  ELF_WOOD("Elf, Wood", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(100))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  ELF_DROW("Elf, Drow", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 5))
      .withBaseWeight(new Weight(75))
      .withSizeModifier(new Dice(2, D6))
      .withWeightMultiplier(new Dice(1, D6))
      .build()), 
  
  ELF_ELADRIN("Elf, Eladrin", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D12))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  ELF_SEA("Elf, Sea", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  ELF_SHADARKAI("Elf, Shadar-kai", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  FIRBOLG("Firbolg", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(6, 2))
      .withBaseWeight(new Weight(175))
      .withSizeModifier(new Dice(2, D12))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  GITH_GITHYANKI("Gith, Githyanki", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 0))
      .withBaseWeight(new Weight(100))
      .withSizeModifier(new Dice(2, D12))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 
  
  GITH_GITHZERAI("Gith, Githzerai", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 11))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D12))
      .withWeightMultiplier(new Dice(1, D4))
      .build()), 
  
  GNOME("Gnome", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(2, 11))
      .withBaseWeight(new Weight(35))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Modifier(1))
      .build()), 
  
  GOBLIN("Goblin", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(3, 5))
      .withBaseWeight(new Weight(35))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Modifier(1))
      .build()), 
  
  GOLIATH("Goliath", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(6, 2))
      .withBaseWeight(new Weight(200))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  HALFELF("Half-elf", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 9))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  HALFORC("Half-orc", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 10))
      .withBaseWeight(new Weight(140))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 
  
  HALFLING("Halfling", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(2, 7))
      .withBaseWeight(new Weight(35))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Modifier(1))
      .build()), 
  
  HOBGOBLIN("Hobgoblin", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  HUMAN("Human", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  KENKU("Kenku", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 4))
      .withBaseWeight(new Weight(50))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(1, D6))
      .build()), 

  KOBOLD("Kobold", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(2, 1))
      .withBaseWeight(new Weight(25))
      .withSizeModifier(new Dice(2, D4))
      .withWeightMultiplier(new Modifier(1))
      .build()), 

  LEONIN("Leonin", HeightWeightGenerator.builder()
       .withBaseHeight(new Height(5, 6))
       .withBaseWeight(new Weight(180))
       .withSizeModifier(new Dice(2, D10))
       .withWeightMultiplier(new Dice(2, D6))
       .build()),
  
  LIZARDFOLK("Lizardfolk", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 9))
      .withBaseWeight(new Weight(120))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 

  LOXODON("Loxodon", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(6, 7))
      .withBaseWeight(new Weight(295))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  MINOTAUR("Minotaur", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 4))
      .withBaseWeight(new Weight(175))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 

  ORC("Orc", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 4))
      .withBaseWeight(new Weight(175))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D6))
      .build()), 

  SATYR("Satyr", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(100))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  SHIFTER("Shifter", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  TABAXI("Tabaxi", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 10))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  TIEFLING("Tiefling", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 9))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D8))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  TRITON("Triton", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 6))
      .withBaseWeight(new Weight(90))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 

  WARFORGED("Warforged", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(5, 10))
      .withBaseWeight(new Weight(270))
      .withSizeModifier(new Dice(2, D6))
      .withWeightMultiplier(new Modifier(4))
      .build()), 

  YUANTI_PUREBLOOD("Yuan-Ti Pureblood", HeightWeightGenerator.builder()
      .withBaseHeight(new Height(4, 8))
      .withBaseWeight(new Weight(110))
      .withSizeModifier(new Dice(2, D10))
      .withWeightMultiplier(new Dice(2, D4))
      .build()), 
  ;
  // @formatter:on

  private final String name;
  private final HeightWeightGenerator generator;

  private Race(String name, HeightWeightGenerator generator) {
    this.name = name;
    this.generator = generator;
  }

  public String getName() {
    return name;
  }

  public Height getMinHeight() {
    return generator.min().getHeight();
  }

  public Weight getMinWeight() {
    return generator.min().getWeight();
  }

  public Height getMaxHeight() {
    return generator.max().getHeight();
  }

  public Weight getMaxWeight() {
    return generator.max().getWeight();
  }

  public Height getAverageHeight() {
    return generator.average().getHeight();
  }

  public Weight getAverageWeight() {
    return generator.average().getWeight();
  }

  public HeightWeight getRandomHeightWeight() {
    return generator.roll();
  }

  public void printHeightWeightStats() {
    System.out.println(name + ":");
    System.out.println();
    HeightWeightStats.stats(generator);
  }


}
