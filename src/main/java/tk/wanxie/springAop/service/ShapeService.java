package tk.wanxie.springAop.service;

import org.springframework.stereotype.Component;
import tk.wanxie.springAop.aspect.Loggable;
import tk.wanxie.springAop.model.Circle;
import tk.wanxie.springAop.model.Triangle;

import javax.annotation.Resource;

@Component
public class ShapeService {

    @Resource
    private Circle circle;

    @Resource
    private Triangle triangle;

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Loggable
    public Triangle getTriangle() {
        return triangle;
    }

    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public String toString() {
        return "ShapeService{" +
                "circle=" + circle +
                ", triangle=" + triangle +
                '}';
    }
}
