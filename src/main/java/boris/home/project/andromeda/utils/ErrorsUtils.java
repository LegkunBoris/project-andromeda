package boris.home.project.andromeda.utils;

import org.apache.commons.lang3.StringUtils;
import java.util.Collection;

public class ErrorsUtils {

  public static String redirectToError(Exception e) {
    return "redirect:/error?text=" + e.toString();
  }

  public static <T> String redirectToError(Collection<T> collection) {
    return "redirect:/error?text=" + StringUtils.join(collection, ";");
  }
}
