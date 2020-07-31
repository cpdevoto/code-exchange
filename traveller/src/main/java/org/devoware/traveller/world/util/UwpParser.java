package org.devoware.traveller.world.util;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.devoware.traveller.world.model.Atmosphere;
import org.devoware.traveller.world.model.Digit;
import org.devoware.traveller.world.model.Government;
import org.devoware.traveller.world.model.Hex;
import org.devoware.traveller.world.model.Hydrographics;
import org.devoware.traveller.world.model.LawLevel;
import org.devoware.traveller.world.model.Population;
import org.devoware.traveller.world.model.Size;
import org.devoware.traveller.world.model.Starport;
import org.devoware.traveller.world.model.TechLevel;
import org.devoware.traveller.world.model.World;

import com.google.common.collect.Sets;

public class UwpParser {
  private static Pattern UWP_PARTIAL = Pattern.compile("([A-EX])([0-9A])([0-9A-C])([0-9A])([0-9A])([0-9A-D])([0-9])\\s*-\\s*([0-9A-HJ-M])");
  private static Pattern UWP = Pattern.compile("\\s*" + UWP_PARTIAL + "\\s*");
  private static Pattern UWP_STANDALONE = Pattern.compile("^" + UWP + "$");
  private static Pattern HEX_PARTIAL = Pattern.compile("([0-9])([0-9])([0-9])([0-9])");
  private static Pattern HEX = Pattern.compile("\\s*" + HEX_PARTIAL + "\\s*");
  private static Pattern HEX_STANDALONE = Pattern.compile("^" + HEX + "$");
  private static Pattern SECTOR_ROW = Pattern.compile("^\\s*[0-9]{4}:\\s*(?:([0-9]{4})\\s+([A-EX][0-9A][0-9A-C][0-9A][0-9A][0-9A-D][0-9]\\s*-\\s*[0-9A-HJ-M])\\s*((?:\\s[GSN])+)?)?\\s*$");
 
  @CheckForNull
  public static World parseSectorRow(@Nonnull String s) {
    Matcher m = requireNonNull(SECTOR_ROW).matcher(requireNonNull(s));
    if (m.find()) {
      String sHex = m.group(1);
      if (sHex == null) {
        return null;
      }
      Hex hex = parseHex(sHex.trim(), HEX_PARTIAL);
      String sUwp = m.group(2).trim(); 
      String sExtensions = m.group(3);
      Set<Extension> extensions;
      if (sExtensions == null) {
        extensions = Sets.newHashSet();
      } else {
        extensions = parseExtensions(sExtensions);
      }
      return parseUwp(sUwp, UWP_PARTIAL, hex, extensions);
    } else {
      throw new RuntimeException("Invalid Sector Row '" + s + "'.");
    }
  }
  
  public static Set<Extension> parseExtensions(@Nonnull String s) {
    Set<Extension> extensions = Sets.newHashSet();
    for (int i = 0; i < s.length(); i++) {
      String sExt = String.valueOf(s.charAt(i));
      if (sExt.trim().isEmpty()) {
        continue;
      }
      Extension ext = Extension.valueOf(sExt);
      if (ext != null) {
        extensions.add(ext);
      }
    }
    return extensions;
  }
  
  public static Hex parseHex(@Nonnull String s) {
    return parseHex(s, HEX_STANDALONE);
  }
  
  private static Hex parseHex(@Nonnull String s, @Nonnull Pattern p) {
    Matcher m = requireNonNull(p).matcher(requireNonNull(s));
    if (m.find()) {
      String d1 = m.group(1);
      String d2 = m.group(2);
      String d3 = m.group(3);
      String d4 = m.group(4);
      String sColumn = (d1 == "0" ? d2 : d1 + d2);
      String sRow = (d3 == "0" ? d4 : d3 + d4);
      int column = Integer.parseInt(sColumn);
      int row = Integer.parseInt(sRow);
      return new Hex(column, row);
    } else {
      throw new RuntimeException("Invalid Hex '" + s + "'.");
    }
  }

  public static World parseUwp(@Nonnull String s) {
    return parseUwp(s, UWP_STANDALONE, new Hex(1, 1), Sets.newHashSet());
  }

  private static World parseUwp(@Nonnull String s, @Nonnull Pattern p, Hex hex, Set<Extension> extensions) {
    Matcher m = requireNonNull(p).matcher(requireNonNull(s));
    if (m.find()) {
      Starport starport = Starport.get(Digit.get(m.group(1)).value());
      Size size = Size.get(Digit.get(m.group(2)).value());
      Atmosphere atmosphere = Atmosphere.get(Digit.get(m.group(3)).value());
      Hydrographics hydrographics = Hydrographics.get(Digit.get(m.group(4)).value());
      Population population = Population.get(Digit.get(m.group(5)).value());
      Government government = Government.get(Digit.get(m.group(6)).value());
      LawLevel lawLevel = LawLevel.get(Digit.get(m.group(7)).value());
      TechLevel techLevel = TechLevel.get(Digit.get(m.group(8)).value());
      boolean gasGiant = extensions.contains(Extension.G);
      boolean navalBase = extensions.contains(Extension.N);
      boolean scoutBase = extensions.contains(Extension.S);
      return new World(hex, starport, navalBase, scoutBase, gasGiant, size, 
          atmosphere, hydrographics, population, government, lawLevel, techLevel);
    } else {
      throw new RuntimeException("Invalid Universal World Profile '" + s + "'.");
    }
  }
  
  private enum Extension {
    G, N, S
  }
  
  
  public static void main(String[] args) {
    World world = UwpParser.parseUwp("X888324 - 2");
    System.out.println(WorldPrinter.prettyPrint(world));
    
    Hex hex = UwpParser.parseHex("0139");
    System.out.println(hex);
    
    Set<Extension> extensions = UwpParser.parseExtensions("G N S");
    System.out.println(extensions);

    extensions = UwpParser.parseExtensions("N");
    System.out.println(extensions);
  
    System.out.println(UwpParser.parseSectorRow("0137: "));
    System.out.println(UwpParser.parseSectorRow("0138: 0138 X88A958 - 1"));
    System.out.println(UwpParser.parseSectorRow("0138: 0138 X88A958 - 1   G S"));
  }
  
  private UwpParser() {}
}
