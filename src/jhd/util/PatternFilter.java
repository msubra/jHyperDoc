package jhd.util;

import java.io.File;
import java.io.FilenameFilter;

public class PatternFilter
    implements FilenameFilter {
  String pattern;

  public PatternFilter(String thePattern) {
    pattern = thePattern.toLowerCase();
  }

  public boolean accept(File dir, String name) {
    // make sure everything's lowercase, so the match will not be
    // case-sensitive
    String ldir = dir.toString().toLowerCase();
    String lname = name.toLowerCase();
    PatternMatch pm = new PatternMatch();

    return ( (pm.isMatch(ldir + File.separator + lname, pattern)) ||
             (pm.isMatch(lname, pattern)));
  }
}