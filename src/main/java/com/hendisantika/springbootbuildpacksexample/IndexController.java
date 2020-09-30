package com.hendisantika.springbootbuildpacksexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-buildpacks-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/10/20
 * Time: 04.40
 */
@RestController
@RequestMapping("/")
public class IndexController {
  @GetMapping
  public String index() {
    return "Spring Build Pack Example. " + LocalDateTime.now();
  }
}

