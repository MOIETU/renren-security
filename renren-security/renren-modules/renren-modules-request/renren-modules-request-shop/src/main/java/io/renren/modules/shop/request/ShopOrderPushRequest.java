package io.renren.modules.shop.request;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.renren.common.base.IDRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
        import java.math.BigDecimal;
    import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 *
 * @author Shark
 * @email shark@126.com
 * @date 2019-08-28 19:54:45
 */
@Data
@Api("订单-发布redis")
public class ShopOrderPushRequest extends IDRequest {


}
