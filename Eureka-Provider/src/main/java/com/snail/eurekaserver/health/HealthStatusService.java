/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	HealthStatusService.java
 * 模块说明：
 * 修改历史：
 * 2020/8/1 - taozhi - 创建。
 */
package com.snail.eurekaserver.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/**
 * @author taozhi
 */
@Service
public class HealthStatusService implements HealthIndicator {

  private Boolean status = true;

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public Health health() {
    if (status) {
      return new Health.Builder().up().build();
    }
    return new Health.Builder().down().build();
  }

  public String getStatus() {
    return this.status.toString();
  }
}
