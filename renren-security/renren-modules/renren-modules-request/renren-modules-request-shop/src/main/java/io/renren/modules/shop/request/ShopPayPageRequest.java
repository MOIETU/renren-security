package io.renren.modules.shop.request;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.renren.common.base.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
        import java.math.BigDecimal;
    import java.io.Serializable;
import java.util.Date;

/**
 * 支付订单
 *
 * @author Shark
 * @email shark@126.com
 * @date 2019-08-28 19:54:44
 */
@Data
@Api("支付订单-分页查询")
public class ShopPayPageRequest extends PageRequest {


}
