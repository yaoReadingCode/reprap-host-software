package org.reprap.geometry;


import org.reprap.Preferences;
import org.reprap.Printer;
import org.reprap.geometry.polygons.*;
import org.reprap.gui.PreviewPanel;
import org.reprap.gui.RepRapBuild;
import org.reprap.machines.MachineFactory;

public class Producer {
	
	protected Printer reprap;
	protected RrLine oddHatchDirection;
	protected RrLine evenHatchDirection;
	protected RepRapBuild bld;
	
	public Producer(PreviewPanel preview, RepRapBuild builder) throws Exception {
		reprap = MachineFactory.create();
		reprap.setPreviewer(preview);
		bld = builder;
		oddHatchDirection = new RrLine(new Rr2Point(0.0, 0.0), new Rr2Point(1.0, 1.0));
		evenHatchDirection = new RrLine(new Rr2Point(0.0, 1.0), new Rr2Point(1.0, 0.0));
	}
	
	public RrPolygon square()
	{
		RrPolygon a = new RrPolygon();
		Rr2Point p1 = new Rr2Point(10, 10);
		Rr2Point p2 = new Rr2Point(20, 10);
		Rr2Point p3 = new Rr2Point(20, 20);
		Rr2Point p4 = new Rr2Point(10, 20);
		a.add(p1, 1);
		a.add(p2, 1);
		a.add(p3, 1);
		a.add(p4, 1);
		return a;
	}
	
	public RrPolygon hex()
	{
		RrPolygon b = new RrPolygon();
		double hexSize = 10;
		double hexLongSize = Math.cos(Math.PI * 30. / 180.0);
		double hexShortSize = Math.sin(Math.PI * 30. / 180.0);
		double hexX = 35, hexY = 15;
		Rr2Point h1 = new Rr2Point(hexX - hexSize / 2.0, hexY - hexSize * hexLongSize);
		Rr2Point h2 = new Rr2Point(hexX - hexSize / 2.0 - hexSize * hexShortSize, hexY);
		Rr2Point h3 = new Rr2Point(hexX - hexSize / 2.0, hexY + hexSize * hexLongSize);
		Rr2Point h4 = new Rr2Point(hexX + hexSize / 2.0, hexY + hexSize * hexLongSize);
		Rr2Point h5 = new Rr2Point(hexX + hexSize / 2.0 + hexSize * hexShortSize, hexY);
		Rr2Point h6 = new Rr2Point(hexX + hexSize / 2.0, hexY - hexSize * hexLongSize);
		b.add(h1, 1);
		b.add(h2, 1);
		b.add(h3, 1);
		b.add(h4, 1);
		b.add(h5, 1);
		b.add(h6, 1);
		return b;
	}
	
	public RrCSGPolygon adriansTestShape()
	{
		Rr2Point p = new Rr2Point(3, 5);
		Rr2Point q = new Rr2Point(7, 27);
		Rr2Point r = new Rr2Point(32, 30);
		Rr2Point s = new Rr2Point(31, 1);
		
		Rr2Point pp = new Rr2Point(12, 21);
		Rr2Point qq = new Rr2Point(18, 32);
		Rr2Point rr = new Rr2Point(15, 17);    
		
		RrHalfPlane ph = new RrHalfPlane(p, q);
		RrHalfPlane qh = new RrHalfPlane(q, r);
		RrHalfPlane rh = new RrHalfPlane(r, s);
		RrHalfPlane sh = new RrHalfPlane(s, p);
		
		RrHalfPlane pph = new RrHalfPlane(pp, qq);
		RrHalfPlane qqh = new RrHalfPlane(qq, rr);
		RrHalfPlane rrh = new RrHalfPlane(rr, pp);
		
		RrCSG pc = new RrCSG(ph);
		RrCSG qc = new RrCSG(qh);
		RrCSG rc = new RrCSG(rh);
		RrCSG sc = new RrCSG(sh);
		
		pc = RrCSG.intersection(pc, qc);
		rc = RrCSG.intersection(sc, rc);		
		pc = RrCSG.intersection(pc, rc);
		
		RrCSG ppc = new RrCSG(pph);
		RrCSG qqc = new RrCSG(qqh);
		RrCSG rrc = new RrCSG(rrh);
		
		ppc = RrCSG.intersection(ppc, qqc);
		ppc = RrCSG.intersection(ppc, rrc);
		ppc = RrCSG.difference(pc, ppc);
		
		pc = ppc.offset(-5);
		ppc = RrCSG.difference(ppc, pc);
		
		RrCSGPolygon result = new RrCSGPolygon(ppc, new 
				RrBox(new Rr2Point(0,0), new Rr2Point(110,110)));
//		result.divide(1.0e-6, 1);
//		new RrGraphics(result, true);
		return result;
	}
	
	public void produce() throws Exception {

        // Fallback defaults
		int extrusionSpeed = 200;
		int extrusionTemp = 40;
		int movementSpeedXY = 230;
		int movementSpeedZ = 230;
		
		try {
			extrusionSpeed = Preferences.loadGlobalInt("ExtrusionSpeed");
			extrusionTemp = Preferences.loadGlobalInt("ExtrusionTemp");
			movementSpeedXY = Preferences.loadGlobalInt("MovementSpeed");
			movementSpeedZ = Preferences.loadGlobalInt("MovementSpeedZ");
		} catch (Exception ex) {
			System.out.println("Warning: could not load ExtrusionSpeed/MovementSpeed, using defaults");
		}
		
		System.out.println("Setting temperature and speed");
		reprap.setTemperature(extrusionTemp);
		reprap.setSpeed(movementSpeedXY);
		reprap.setSpeedZ(movementSpeedZ);
		System.out.println("Intialising reprap");
		reprap.initialise();
		System.out.println("Selecting material");
		reprap.selectMaterial(0);
		reprap.setExtruderSpeed(extrusionSpeed);

		// A "warmup" segment to get things in working order
		System.out.println("Printing warmup segments, moving to (0,5)");
		reprap.moveTo(0, 5, 0);
		System.out.println("Printing warmup segments, printing to (0,20)");
		reprap.printTo(0, 20, 0);
		System.out.println("Printing warmup segments, printing to (2,20)");
		reprap.printTo(2, 20, 0);
		System.out.println("Printing warmup segments, printing to (2,5)");
		reprap.printTo(2, 5, 0);
		
		// This should now split off layers one at a time
		// and pass them to the LayerProducer.  At the moment,
		// we just construct a simple test layer and produce that.

		boolean isEvenLayer = true;
		STLSlice stlc = new STLSlice(bld.getSTLs());
		for(double z = 0; z < 5.0; z += reprap.getExtrusionHeight()) {

			if (reprap.isCancelled())
				break;
			System.out.println("Commencing layer at " + z);

			// Change Z height
			reprap.moveTo(reprap.getX(), reprap.getY(), z);
			
			if (reprap.isCancelled())
				break;
			RrPolygonList list = new RrPolygonList();

// ************ Simon's example start
			
			// Add a square block

			list.add(square());
	
			// Add a hex block
			
			list.add(hex());

			LayerProducer layer = new LayerProducer(reprap, list,
					isEvenLayer?evenHatchDirection:oddHatchDirection);
			
// ************ Simon's examples end - Adrian's start
			
//			LayerProducer layer = new LayerProducer(reprap, stlc.slice(z+0.01),
//					isEvenLayer?evenHatchDirection:oddHatchDirection);
// ************ Adrian's example end.
			
			layer.plot();
			
			isEvenLayer = !isEvenLayer;
		}
		
		reprap.moveTo(0, 0, reprap.getZ());
		
		reprap.terminate();

	}

	public double getTotalDistanceMoved() {
		return reprap.getTotalDistanceMoved();
	}
	
	public double getTotalDistanceExtruded() {
		return reprap.getTotalDistanceExtruded();
	}
	
	public double getTotalVolumeExtruded() {
		return reprap.getTotalDistanceExtruded() * reprap.getExtrusionHeight() * reprap.getExtrusionSize();
	}
	
	public void dispose() {
		reprap.dispose();
	}
	
}
